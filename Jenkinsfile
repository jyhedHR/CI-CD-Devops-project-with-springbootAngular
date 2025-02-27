pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }
    environment {
        DOCKER_IMAGE = "jyhedhr"
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
                sh 'mvn clean compile'
            }
        }

        stage('Test Project') {
            steps {
                sh 'mvn -Dtest=SkierServicesImplTest clean test'
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
                sh 'sudo docker build -t gestion-station-ski-1.0 .'  // Removed sudo
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: 'DockerHub', variable: 'DOCKER_ACCESS_TOKEN')]) {
                    sh 'echo $DOCKER_ACCESS_TOKEN | docker login -u jyhedhr --password-stdin'
                    sh 'docker tag gestion-station-ski-1.0 jyhedhr/jyhedhr:latest'
                    sh 'docker push jyhedhr/jyhedhr:latest'
                }
            }
        }

        stage('Deploy Container') {
            steps {
                sh 'docker stop skiReg || true'
                sh 'docker rm skiReg || true'
                sh 'docker run -d --name skiReg -p 8089:8089 jyhedhr/jyhedhr:latest'
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
    }
}
