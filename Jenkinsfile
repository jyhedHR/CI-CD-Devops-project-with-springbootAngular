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
                 sh 'mvn -Dtest=services.RegistrationServiceImplTest test'
             }
         }
 stage ('SonarQube analysis') {
steps{
withSonarQubeEnv('SonarQube') {
  sh 'mvn sonar:sonar '
}
}
}

}


}