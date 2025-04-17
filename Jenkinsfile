pipeline {

 agent any

 environment {
         JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64/"
         M2_HOME = "/opt/apache-maven-3.6.3"
         PATH = "$M2_HOME/bin:$PATH"
     }

 stages {

 stage('GIT') {

           steps {

               git branch: 'NehdiEya_4TWIN5_Groupe2',

               url: 'git@github.com:jyhedHR/4twin5_Group2_gestion-station-skier.git'

          }

     }

 stage ('Compile Stage') {

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
         always {
             emailext(
                 subject: "Pipeline Status: ${currentBuild.currentResult}",
                 body: """
                     <html>
                     <body>
                         <p><b>Build Status:</b> ${currentBuild.currentResult}</p>
                         <p><b>Build Number:</b> ${currentBuild.number}</p>
                         <p><b>Job:</b> ${env.JOB_NAME}</p>
                         <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                     </body>
                     </html>
                 """,
                 mimeType: 'text/html',
                 to: "nehdieya02@gmail.com"
             )
         }
     }

}
