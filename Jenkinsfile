pipeline {
    
    agent any
    
    tools { 
        maven 'MVN3'  
        jdk 'JDK8' 
    }
    
    options {
        buildDiscarder(logRotator(daysToKeepStr:'60',numToKeepStr:'-1', artifactDaysToKeepStr: '30',artifactNumToKeepStr:'-1'))
    }
    

    parameters {
        string(name: 'URL_SCM', defaultValue: "https://github.com/CastInfo/robotest.git", description: 'Project SCM url.')
        string(name: 'SCM_CREDENTIALS', description: 'SCM Credentials.')
        string(name: 'SCM_BRANCH', defaultValue: "develop", description: 'SCM branch.')
        booleanParam(name: 'INSTALL_BOM', defaultValue: false, description: 'Mvn install bom')
        booleanParam(name: 'INSTALL_CORE', defaultValue: false, description: 'Mvn install core')
        booleanParam(name: 'INSTALL_GENERATOR', defaultValue: false, description: 'Mvn install generator')
        booleanParam(name: 'INSTALL_SUITEUI', defaultValue: false, description: 'Mvn install suite ui')
    }      
     
    stages {
    
        stage('SCM') {
            steps {     
                git(
                    url: "${params.URL_SCM}",
                    credentialsId: "${params.SCM_CREDENTIALS}",
                    branch: "${params.SCM_BRANCH}" 
                )
            }               
        }
        
        stage('INSTALL ROBOTEST BOM') {
            when {
                expression {
                    return "${params.INSTALL_BOM}" == "true"
                }
            }
            steps {
                sh "mvn -U -e -f bom/pom.xml install"
            }               
        }
        
        
        stage('INSTALL ROBOTEST CORE') {
            when {
                expression {
                    return "${params.INSTALL_CORE}" == "true"
                }
            }
            steps {
                sh "mvn -U -e -f core/pom.xml install"
            }               
            post {
                always {
                      junit "**/target/surefire-reports/*.xml"
                }
            }   
        }
        
        stage('INSTALL ROBOTEST SUITE/CASE GENERATOR') {
            when {
                expression {
                    return "${params.INSTALL_GENERATOR}" == "true"
                }
            }
            steps {
                sh "mvn -U -e -f suite-cases-json-generator/pom.xml install"
            }               
        }

        stage('INSTALL ROBOTEST SUITE UI') {
            when {
                expression {
                    return "${params.INSTALL_SUITEUI}" == "true"
                }
            }
            steps {
                nodejs(nodeJSInstallationName: 'NODE9') {
                    sh "mvn -U -e -f standalone-suite-report-ui/pom.xml install -PinstallTooling"
                }
            }     
            post {
                always {
                      cleanWs()
                }
            }             
        }

     }
}

