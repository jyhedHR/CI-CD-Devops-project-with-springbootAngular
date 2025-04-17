pipeline {

    agent any

   environment {
       JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64/"
       M2_HOME = "/opt/apache-maven-3.6.3"
       PATH = "$M2_HOME/bin:$PATH"
       DOCKERHUB_USER = "eyanehdi"
       IMAGE_NAME = "eyanehdi-groupe2-gestion-station-ski"   // ✅ no colon here
       IMAGE_TAG = "1.0"
       LOCAL_IMAGE = "${IMAGE_NAME}:${IMAGE_TAG}"
       REMOTE_IMAGE = "${DOCKERHUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
   }



    stages {

        stage('GIT') {
            steps {
                git branch: 'NehdiEya_4TWIN5_Groupe2',
                    url: 'git@github.com:jyhedHR/4twin5_Group2_gestion-station-skier.git'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test Stage') {
            steps {
                sh 'mvn -X test'
            }
        }

        stage('Jacoco Report') {
            steps {
                sh 'mvn jacoco:report'
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
                    def imageExists = sh(script: "docker images -q eyanehdi_groupe2_gestion-station-ski:1.0", returnStdout: true).trim()
                    if (!imageExists) {
                        echo "Image not found, building..."
                        sh "docker build -t eyanehdi_groupe2_gestion-station-ski:1.0 ."
                    } else {
                        echo "Image already exists, skipping build."
                    }
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'DockerHub',
                        usernameVariable: 'DOCKERHUB_USER',
                        passwordVariable: 'DOCKERHUB_PASS'
                    )
                ]) {
                    sh "docker tag ${LOCAL_IMAGE} ${REMOTE_IMAGE}"
                    sh "echo $DOCKERHUB_PASS | docker login -u $DOCKERHUB_USER --password-stdin"
                    sh "docker push ${REMOTE_IMAGE}"
                    sh "docker logout"
                }
            }
        }



        stage('Docker Compose build') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Docker Compose Up') {
            steps {
                sh 'docker compose up -d'
            }
        }

        stage('Mailing Test') {
            steps {
                echo "✅ Envoi de mail de test réussi."
                mail to: 'nehdieya02@gmail.com',
                     subject: 'Test simple Jenkins mail',
                     body: 'This is a plain Jenkins email using the basic "mail" step.'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline exécuté avec succès.'
            mail to: 'nehdieya02@gmail.com',
                 subject: 'Succès du Pipeline - gestionski',
                 body: """
Bonjour Eya,

Le pipeline Jenkins s’est exécuté avec succès. 🎉

✔ Projet : gestionski
📅 Date : ${new Date()}

Cordialement,
Jenkins
"""
        }

        failure {
            echo '❌ Le pipeline a échoué.'
            mail to: 'nehdieya02@gmail.com',
                 subject: 'Échec du Pipeline - gestionski',
                 body: """
Bonjour Eya,

Le pipeline Jenkins a échoué. 🚨

✔ Projet : gestionski
📅 Date : ${new Date()}

Merci de consulter Jenkins pour plus de détails.

Cordialement,
Jenkins
"""
        }
    }
}
