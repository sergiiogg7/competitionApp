pipeline {
    agent any
    tools {
        maven 'mavenjenkins'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}