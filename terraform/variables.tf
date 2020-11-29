variable "ecs_cluster_name" {
    description = "The name of the Amazon ECS cluster."
}

variable "security_group_ids" {
  type = "list"
}

variable "log_retention_in_days" {
  default = "14"
}

variable "aws_ecs_task_name" {}

variable "container_cpu" {
  default = "256"
}

variable "container_memory" {
    default = "300"
}

variable "container_image" {
    default = "517900312471.dkr.ecr.ap-southeast-2.amazonaws.com/mathapp:latest"
 }

variable "region" {
    description = "The AWS region to create resources in."
    default = "ap-southeast-2"
}

variable "container_port" {
  default = 8080
}

variable "alb_subnet_ids" {
  type = "list"
}

variable "alb_type" {
  default = "application"
}

variable "aws_vpc" {}

variable "aws_subnet" {
    type = "list"
}

variable "deregistration_delay" {
  default = 60
}

variable "ecs_image_id" {
  default=""
}

variable "ecs_instance_type" {
  default = "t2.micro"
}







variable "aws_access_key" {
    description = "The AWS access key."
}

variable "aws_secret_key" {
    description = "The AWS secret key."
}


# TODO: support multiple availability zones, and default to it.
variable "availability_zone" {
    description = "The availability zone"
    default = "us-east-1a"
}


variable "amis" {
    description = "Which AMI to spawn. Defaults to the AWS ECS optimized images."
    # TODO: support other regions.
    default = {
        us-east-1 = "ami-ddc7b6b7"
    }
}


variable "autoscale_min" {
    default = "1"
    description = "Minimum autoscale (number of EC2)"
}

variable "autoscale_max" {
    default = "10"
    description = "Maximum autoscale (number of EC2)"
}

variable "autoscale_desired" {
    default = "4"
    description = "Desired autoscale (number of EC2)"
}


variable "instance_type" {
    default = "t2.micro"
}

variable "ssh_pubkey_file" {
    description = "Path to an SSH public key"
    default = "~/.ssh/id_rsa.pub"
}