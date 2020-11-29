provider "aws" {
    access_key = "${var.aws_access_key}"
    secret_key = "${var.aws_secret_key}"
    region = "${var.region}"
}

resource "aws_ecs_cluster" "mathapp" {
    name = "${var.ecs_cluster_name}"
}

resource "aws_security_group" "lb_sg" {
    name = "sg-alb"
    description = " ECS-ALB Security Group"
    vpc_id = "${var.aws_vpc}"
    tags {
        Name = "SG for ALB"
    }
}

resource "aws_security_group_rule" "alb_http" {
  type              = "ingress"
  from_port         = 8080
  to_port           = 8080
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = "${aws_security_group.lb_sg.id}"
}

resource "aws_security_group_rule" "alb_ssh" {
  type              = "ingress"
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = "${aws_security_group.lb_sg.id}"
}

resource "aws_security_group_rule" "allow_alb_in" {
  count                    = "${length(var.security_group_ids)}"
  type                     = "ingress"
  from_port                = 31000
  to_port                  = 61000
  protocol                 = "tcp"
  source_security_group_id = "${aws_security_group.lb_sg.id}"
  security_group_id        = "${element(var.security_group_ids, count.index)}"
}

resource "aws_cloudwatch_log_group" "main" {
  name              = "ECSLogGroup-"
  retention_in_days = "${var.log_retention_in_days}"
}

resource "aws_ecs_task_definition" "task" {
  family                   = "${var.aws_ecs_task_name}"
  container_definitions = <<DEFINITION
[
  {
    "cpu": ${var.container_cpu},
    "portMappings": [
      {
        "containerPort": ${var.container_port}
      }
    ],
    "essential": true,
    "image": "${var.container_image}",
    "memory": ${var.container_memory},
    "name": "mathapp",
    "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
            "awslogs-group": "${aws_cloudwatch_log_group.main.name}",
            "awslogs-region": "${var.region}",
            "awslogs-stream-prefix": "mathapp"
        }
    }
    
  }
]
DEFINITION
}

resource "aws_lb" "mathapp_alb" {
  name               = "mathapp_alb"
  internal           = false
  load_balancer_type = "${var.alb_type}"
  idle_timeout       = 60
  security_groups    = [aws_security_group.lb_sg.id]
  subnets            = ["${var.alb_subnet_ids}"]
  tags = {
    Name = "mathapp_alb"
  }
}

resource "aws_alb_listener" "front_end" {
  load_balancer_arn = "${aws_alb.mathapp_alb.id}"
  port     = "8080"
  protocol = "HTTP"
  default_action {
    target_group_arn = "${aws_alb_target_group.main.id}"
    type             = "forward"
  }
}

resource "aws_lb_listener_rule" "host_based_weighted_routing" {
  listener_arn = aws_alb_listener.front_end.arn
  priority     = 1
  action {
    type             = "forward"
    target_group_arn = aws_alb_target_group.main.arn
  }
  condition {
    path_pattern {
      values = [ "/" ]
    }
  }
}

resource "aws_alb_target_group" "main" {
  name                 = "ECSTG"
  port                 = "${var.container_port}"
  protocol             = "HTTP"
  vpc_id               = "${var.aws_vpc}"
  deregistration_delay = "${var.deregistration_delay}"
  target_type          = "ip"

  health_check {
    path                = "/"
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
    interval            = 10
    matcher             = "200-399"
  }
}

resource "aws_autoscaling_group" "bar" {
  max_size                  = 1
  min_size                  = 1
  desired_capacity          = 1
  placement_group           = aws_placement_group.test.id
  launch_configuration      = aws_launch_configuration.linux2.name
  vpc_zone_identifier       = "${var.aws_subnet}"

  timeouts {
    delete = "15m"
  }
}

data "aws_launch_configuration" "linux2" {
  name = "test-launch-config"
  image_id = "${var.ecs_image_id}"
  instance_type = "${var.ecs_instance_type}"
  security_groups = ["${aws_security_group.lb_sg.id}"]
  iam_instance_profile = "${aws_iam_instance_profile.ecs.name}"
  user_data = "#!/bin/bash\necho ECS_CLUSTER='${var.ecs_cluster_name}' > /etc/ecs/ecs.config\n yum install -y aws-cfn-bootstrap\n  /opt/aws/bin/cfn-signal -e $? --stack mathapp-stack --resource ECSAutoScalingGroup --region ap-southeast-2"
}

resource "aws_iam_instance_profile" "ecs" {
    name = "ecs-instance-profile"
    path = "/"
    roles = ["${aws_iam_role.ecs_host_role.name}"]
}

resource "aws_iam_role" "ecs_host_role" {
    name = "ecs_host_role"
    assume_role_policy = "${file("policies/ecs-role.json")}"
}

resource "aws_iam_role_policy" "ecs_instance_role_policy" {
    name = "ecs_instance_role_policy"
    policy = "${file("policies/ecs-instance-role-policy.json")}"
    role = "${aws_iam_role.ecs_host_role.id}"
}

resource "aws_ecs_service" "default" {
  cluster                            = "${var.ecs_cluster_name}"
  task_definition                    = aws_ecs_task_definition.task.arn
  desired_count                      = 1
  launch_type                        = "${var.launch_type}"

  network_configuration {
    security_groups = ["${var.security_group_ids}"]
    subnets         = ["${var.container_subnets}"]
  }

  load_balancer = [{
    target_group_arn = "${aws_alb_target_group.main.id}"
    container_name   = "${module.task_label.id}"
    container_port   = "${var.container_port}"
  }]

  #["${local.load_balancer["${var.container_port != "" ? "with_alb" : "without_alb"}"]}"]

  lifecycle {
    #create_before_destroy = true
    ignore_changes = ["desired_count"]
  }
  service_registries {
    registry_arn = "${aws_service_discovery_service.service.arn}"
  }
  depends_on = [
    "aws_alb_listener.front_end",
  ]
}




resource "aws_ecs_service" "mongo" {
  name            = "mongodb"
  cluster         = aws_ecs_cluster.foo.id
  task_definition = aws_ecs_task_definition.mongo.arn
  desired_count   = 3
  iam_role        = aws_iam_role.foo.arn
  depends_on      = [aws_iam_role_policy.foo]

  ordered_placement_strategy {
    type  = "binpack"
    field = "cpu"
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.foo.arn
    container_name   = "mongo"
    container_port   = 8080
  }

  placement_constraints {
    type       = "memberOf"
    expression = "attribute:ecs.availability-zone in [us-west-2a, us-west-2b]"
  }
}














resource "aws_key_pair" "alex" {
    key_name = "alex-key"
    public_key = "${file(var.ssh_pubkey_file)}"
}

resource "aws_vpc" "main" {
    cidr_block = "10.0.0.0/16"
    enable_dns_hostnames = true
}

resource "aws_route_table" "external" {
    vpc_id = "${var.aws_vpc}"
    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = "${aws_internet_gateway.main.id}"
    }
}

resource "aws_route_table_association" "external-main" {
    subnet_id = "${aws_subnet.main.id}"
    route_table_id = "${aws_route_table.external.id}"
}


resource "aws_internet_gateway" "main" {
    vpc_id = "${aws_vpc.main.id}"
}

resource "aws_security_group" "load_balancers" {
    name = "load_balancers"
    description = "Allows all traffic"
    vpc_id = "${aws_vpc.main.id}"

    # TODO: do we need to allow ingress besides TCP 80 and 443?
    ingress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    # TODO: this probably only needs egress to the ECS security group.
    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}


resource "aws_iam_role" "ecs_service_role" {
    name = "ecs_service_role"
    assume_role_policy = "${file("policies/ecs-role.json")}"
}

resource "aws_iam_role_policy" "ecs_service_role_policy" {
    name = "ecs_service_role_policy"
    policy = "${file("policies/ecs-service-role-policy.json")}"
    role = "${aws_iam_role.ecs_service_role.id}"
}
