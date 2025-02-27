
pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "yasminebouteraa"  // Nom en minuscules
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

        stage ('Generate jar ') {

                  steps {

                  sh 'mvn package -DskipTests'

                  }
                  }



        stage(' test Projet') {
            steps {
                 sh 'mvn test'
             }
        }


stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }
       stage('Push Docker Image') {
           steps {
               // Utilisez le nom d'identifiant exact de vos credentials DockerHub
               withCredentials([string(credentialsId: 'DockerHub', variable: 'DOCKER_ACCESS_TOKEN')]) {
                   // Connexion à Docker Hub
                   sh 'echo $DOCKER_ACCESS_TOKEN | docker login -u yasminebouteraa --password-stdin'

                   // Taguer l'image Docker
                   sh 'docker tag $DOCKER_IMAGE:$DOCKER_TAG yasminebouteraa/$DOCKER_IMAGE:$DOCKER_TAG'

                   // Pousser l'image vers Docker Hub
                   sh 'docker push yasminebouteraa/$DOCKER_IMAGE:$DOCKER_TAG'
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
