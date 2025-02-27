pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }
    environment {
        DOCKER_IMAGE = "jyhedhr/gestion-station-ski"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Hi Jihed'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'Jihed_Horchani_4twin5',
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git',
                    credentialsId: 'Gitttt'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean package -DskipTests'  // Ensure JAR is built
            }
        }

        stage('Test Project') {
            steps {
                sh 'mvn -Dtest=SkierServicesImplTest test'
            }
        }

        stage('Run SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'sudo docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .'  // Removed sudo
            }
        }
       stage('Run Docker Container') {
            steps {
                sh 'sudo docker run -d -p 8089:8089 --name gestion-station-ski ${DOCKER_IMAGE}:${DOCKER_TAG}'
            }
        }

   

        stage('Deploy Container') {
            steps {
                sh 'sudo docker stop skiReg || true'
                sh 'sudo docker rm skiReg || true'
                sh 'sudo docker run -d --name skiReg -p 8089:8089 ${DOCKER_IMAGE}:${DOCKER_TAG}'
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
    }
}
