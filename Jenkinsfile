pipeline {
    agent any
    environment {
      JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64/"
       M2_HOME = "/opt/apache-maven-3.6.3"
        DOCKERHUB_USER = "yasminebouteraa"
               IMAGE_NAME = "bouteraayasmine-4twin5-g2-gestion-station-ski"
               IMAGE_TAG = "1.0"
               LOCAL_IMAGE = "${IMAGE_NAME}:${IMAGE_TAG}"
               REMOTE_IMAGE = "${DOCKERHUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
    }

    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'salut je suis Yasmine Bouteraa'
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

        stage('Generate jar') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running JUnit Tests...'
                sh 'mvn test'
            }
        }

        stage('Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

         stage('Build Docker Image') {
                    steps {
                        script {
                            def imageExists = sh(script: "docker images -q ${LOCAL_IMAGE}", returnStdout: true).trim()
                            if (!imageExists) {
                                echo "Image not found"
                                sh "docker build -t ${LOCAL_IMAGE}  ."
                            } else {
                                echo "Image already exists"
                            }
                        }
                    }
                }
               stage('Push to DockerHub') {
                   steps {
                       withCredentials([
                           usernamePassword(
                               credentialsId: 'dockerhub-credentials',
                               usernameVariable: 'DOCKERHUB_USER',
                               passwordVariable: 'DOCKERHUB_PASS'
                           )
                       ]) {
                           sh "docker tag ${LOCAL_IMAGE} ${REMOTE_IMAGE}"
                           sh 'echo "$DOCKERHUB_PASS" | docker login -u "$DOCKERHUB_USER" --password-stdin'
                           sh "docker push ${REMOTE_IMAGE}"
                           sh "docker logout"
                       }
                   }
               }



        stage('Docker Compose Up') {
            steps {
          sh 'docker compose build'
                sh 'docker compose up -d'
            }
        }

        stage('Mail Test') {
            steps {
                echo "Envoi du mail de test en cours..."
                mail to: 'yasminebouteraa21@gmail.com',
                     subject: 'Jenkins Test mail',
                     body: '''Bonjour
                          Ceci est un e-mail de test envoyé automatiquement par Jenkins pour vérifier la configuration de l'envoi de mails
                            Si vous recevez ce message, cela signifie que tout fonctionne correctement
                             Cordialement
                             Votre serveur Jenkins'''

            }
        }
    }

    post {
        success {
            echo '✅ Le pipeline Jenkins pour le projet "gestionski" a terminé sans erreurs.'
            mail to: 'yasminebouteraa21@gmail.com',
                 subject: 'Succès du Pipeline - gestionski',
                 body: """
Salut Bouteraa,

Le pipeline Jenkins s’est exécuté avec succès.

✔ Projet : gestionski
Date : ${new Date()}

Cordialement,
Jenkins
"""
        }

        failure {
            echo 'Une erreur s’est produite lors de l’exécution du pipeline Jenkins.'
            mail to: 'yasminebouteraa21@gmail.com',
                 subject: 'Échec du Pipeline - gestionski',
                 body: """
Salut Bouteraa,

Le pipeline Jenkins a échoué.

✔ Projet : gestionski
📅 Date : ${new Date()}

Merci de consulter Jenkins pour autre informations ou vérifications.

Cordialement,
Jenkins
"""
        }
    }



}
