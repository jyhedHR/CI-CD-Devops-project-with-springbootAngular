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
                echo 'salut Yasmine Bouteraa'
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
                        echo "Image not found, building..."
                        sh "docker build -t gestion-station-ski:1.0 ."
                    } else {
                        echo "Image already exists, skipping build."
                    }
                }
            }
        }

        stage('Docker Compose Up') {
                    steps {
                        sh 'docker compose up -d'
                    }
                }


    }
}
