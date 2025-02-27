
pipeline {
    agent any
    environment {
            DOCKER_IMAGE = "skierDevops"
            DOCKER_TAG = "latest"
        }
    tools {
        maven 'M2_HOME'


    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'salut Yassmine bouterra'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'Yasmine_bouteraa_4TWIN5',
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git',
                    credentialsId: 'git'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean compile'
            }
        }


        stage(' test Projet') {
            steps {
                 sh 'mvn =test'
             }
        }
        stage ('SonarQube analysis') {
        steps{
        withSonarQubeEnv('SonarQube') {
        sh 'mvn sonar:sonar '
}
}
}

stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }
        stage('Push Docker Image') {
            steps {
            withCredentials([string(credentialsId: 'DockerHub', variable: 'DOCKER_ACCESS_TOKEN')]) {
                sh 'echo $DOCKER_ACCESS_TOKEN | docker login -u yasminebouteraa--password-stdin'
                 sh 'docker tag yasminebouteraa:latest yasminebouteraa/$DOCKER_IMAGE:$DOCKER_TAG'
            sh 'docker push $DOCKER_IMAGE:$DOCKER_TAG'

            }
            }
        }
        stage('Deploy Container') {
                    steps {
                        sh 'docker stop skiReg || true'
                        sh 'docker rm skiReg || true'
                        sh 'docker run -d --name skiReg -p 8089:8089 $DOCKER_IMAGE:$DOCKER_TAG'
                    }
                }


    }
}
