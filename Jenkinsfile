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

        stage('Run Unit Tests') {
            steps {
                echo 'Running JUnit Tests...'
                sh 'mvn test'
            }
        }

        stage('Integration Tests') {
            steps {
                echo 'Running Integration Tests...'
                sh 'mvn verify'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t yasminebouteraa/$DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: 'DockerHub', variable: 'DOCKER_ACCESS_TOKEN')]) {
                    sh 'echo $DOCKER_ACCESS_TOKEN | docker login -u yasminebouteraa --password-stdin'
                    sh 'docker push yasminebouteraa/$DOCKER_IMAGE:$DOCKER_TAG'
                }
            }
        }

        stage('Deploy Container') {
            steps {
                sh 'docker stop skiReg || true'
                sh 'docker rm skiReg || true'
                sh 'docker run -d --name skiReg -p 8089:8089 yasminebouteraa/$DOCKER_IMAGE:$DOCKER_TAG'
            }
        }
    }
}
