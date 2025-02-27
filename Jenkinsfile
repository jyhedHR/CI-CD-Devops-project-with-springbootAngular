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

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: 'DockerHub1', variable: 'DOCKER_ACCESS_TOKEN')]) {
                    sh 'echo $DOCKER_ACCESS_TOKEN | docker login -u jyhedhr --password-stdin'
                    sh 'sudo docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:${DOCKER_TAG}'
                    sh 'sudo docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
                }
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
