pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -B test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t docker-demo:latest .'
            }
        }
    }

    post {
        success {
            echo 'Pipeline finished successfully. Image docker-demo:latest was built.'
        }
        failure {
            echo 'Pipeline failed. Check the stage logs above.'
        }
    }
}
