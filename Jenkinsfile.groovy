pipeline {
    agent any
    tools {
        maven 'maven jenkins'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Falta por preparar para ejecutar los tests'
            }
        }
        stage('Deploy') {
            steps {
                sh 'java -jar target/competitionApp-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}