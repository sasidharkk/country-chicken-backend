pipeline {
    agent any

    tools {
        maven 'maven3.9.12'
        jdk 'java17'
    }

    options {
        skipDefaultCheckout(true)
    }

    environment {
        APP_NAME = 'country-chicken-backend'

        NEXUS_MAVEN_URL  = '13.232.55.198:8081'
        NEXUS_DOCKER_URL = '13.232.55.198:8082'

        MAVEN_REPO  = 'maven-releases'
        DOCKER_REPO = 'docker-releases'

        GROUP_ID = 'com.countrychicken'
        VERSION  = ''
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/sasidharkk/country-chicken-backend.git'
            }
        }

        stage('Read Version') {
            steps {
                script {
                    VERSION = sh(
                        script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
                        returnStdout: true
                    ).trim()

                    if (!VERSION) {
                        error "❌ Version not found in pom.xml"
                    }

                    echo "✅ Project Version: ${VERSION}"
                }
            }
        }
        stage('Build & Deploy JAR to Nexus') {
    steps {
        withCredentials([
            usernamePassword(
                credentialsId: 'nexus-credentials',
                usernameVariable: 'NEXUS_USER',
                passwordVariable: 'NEXUS_PASS'
            )
        ]) {
            sh """
mvn clean deploy -DskipTests \
--DaltDeploymentRepository=nexus-snapshots::default::http://13.232.55.198:8081/repository/maven-snapshots/
"""
        }
    }
}
        stage('Build Docker Image') {
            steps {
                sh """
                docker build \
                  -t ${NEXUS_DOCKER_URL}/${DOCKER_REPO}/${APP_NAME}:${VERSION} .
                """
            }
        }

        stage('Push Docker Image to Nexus') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-nexus-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                    echo "$DOCKER_PASS" | docker login ${NEXUS_DOCKER_URL} -u "$DOCKER_USER" --password-stdin
                    docker push ${NEXUS_DOCKER_URL}/${DOCKER_REPO}/${APP_NAME}:${VERSION}
                    docker logout ${NEXUS_DOCKER_URL}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ CI Pipeline completed successfully"
        }
        failure {
            echo "❌ CI Pipeline failed"
        }
        always {
            sh 'docker info >/dev/null 2>&1 || true'
            cleanWs()
        }
    }
}
