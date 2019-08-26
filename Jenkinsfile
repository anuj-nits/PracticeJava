#!/usr/bin/env groovy
import hudson.model.*
import hudson.EnvVars
import java.net.URL

node{
    stage('Git Checkout'){
        git credentialsId: '23ea5119-e03a-4c2d-8697-8c8a95efd3e6', url: 'https://github.com/anuj-nits/launch.git'
    }
    stage('withoutDocker'){
        sh './gradlew withoutDocker'
    }
    stage('withDocker'){
        sh './gradlew withDocker'
    }
        stage('withoutDockerTestng'){
        sh './gradlew withoutDockerTestng'
    }
        stage('withDockerTestng'){
        sh './gradlew withDockerTestng'
    }
        stage('helloWorld'){
        sh './gradlew helloWorld'
        }
}
