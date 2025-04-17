pipeline {
    agent any

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64/"
        M2_HOME = "/opt/apache-maven-3.6.3"
        PATH = "$M2_HOME/bin:$PATH"

        AZURE_SUBSCRIPTION_ID = "1c9a7c13-fd77-476c-8041-ce7950715530"
        AZURE_CLIENT_ID = credentials('azure_client_id')
        AZURE_CLIENT_SECRET = credentials('azure_client_secret')
        AZURE_TENANT_ID = "604f1a96-cbe8-43f8-abbf-f8eaf5d85730"
        RESOURCE_GROUP = "MyResourceGroup"
        PROD_VM_NAME = "prod-vm"
        TEST_VM_NAME = "test-vm"
    }

    stages {

        stage('GIT') {
            steps {
                script {
                    git branch: 'NehdiEya_4TWIN5_Groupe2',
                        url: 'git@github.com:jyhedHR/4twin5_Group2_gestion-station-skier.git'
                }
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
                    def imageExists = sh(script: "docker images -q gestion-station-ski:1.0", returnStdout: true).trim()
                    if (!imageExists) {
                        echo "Image not found, building..."
                        sh "docker build -t gestion-station-ski:1.0 ."
                    } else {
                        echo "Image already exists, skipping build."
                    }
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
    }

    post {
        success {
            echo '✅ Pipeline exécuté avec succès.'
            emailext(
                subject: "✅ Succès du Pipeline - DevopsSkiStation",
                body: """
                    Bonjour,

                    Le pipeline Jenkins s’est exécuté avec succès. 🎉

                    ✔ Projet : DevopsSkiStation
                    📅 Date : ${new Date()}

                    Cordialement,
                    Jenkins
                """,
                to: 'negamex4274@gmail.com'
            )
        }

        failure {
            echo '❌ Le pipeline a échoué.'
            emailext(
                subject: "❌ Échec du Pipeline - DevopsSkiStation",
                body: """
                    Bonjour,

                    Le pipeline Jenkins a échoué. 🚨

                    ✔ Projet : DevopsSkiStation
                    📅 Date : ${new Date()}

                    Merci de consulter Jenkins pour plus de détails.

                    Cordialement,
                    Jenkins
                """,
                to: 'negamex4274@gmail.com'
            )
        }
    }
}
