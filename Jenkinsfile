pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "gestion-station-ski"
        DOCKER_TAG = "latest"
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
                    def imageExists = sh(script: "docker images -q gestion-station-ski:1.0", returnStdout: true).trim()
                    if (!imageExists) {
                        echo "Image not found"
                        sh "docker build -t gestion-station-ski:1.0 ."
                    } else {
                        echo "Image  exists"
                    }
                }
            }
        }

        stage('Docker Compose Up') {
                    steps {
                        sh 'docker compose up -d'
                    }
                }






    stage('Mail Test') {
        steps {
            echo " Envoi du mail de test en cours..."
            mail to: '23yass23yass@gmail.com',
                 subject: ' Jenkins Test  mail',
                 body: 'This is a plain Jenkins email using the basic "mail" step.'

        }
    }

    post {
        success {
            echo ' Le pipeline Jenkins pour le projet "gestionski" a terminé sans erreurs.'
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
            echo ' Une erreur s’est produite lors de l’exécution du pipeline Jenkins.'
            mail to: 'yasminebouteraa21@gmail.com',
                 subject: 'Échec du Pipeline - gestionski',
                 body: """
    Salut Bouteraa,

    Le pipeline Jenkins a échoué.

    ✔ Projet : gestionski
    Date : ${new Date()}

    Merci de consulter Jenkins pour autre informations ou verifications.

    Cordialement,
    Jenkins
    """
        }
    }

}}
