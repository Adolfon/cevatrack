node {
   def mvnHome
   
   stage('Preparation') {
      def scmVars = checkout([
        $class: 'GitSCM', 
        branches : [[name: '*/develop']],
        doGenerateSubmoduleConfigurations: false, 
        extensions: [[$class: 'LocalBranch', localBranch: "**"]], 
        submoduleCfg: [], 
        userRemoteConfigs: [
            [
                url: 'ssh://git@gitlab.fifthlayer.net:2222/cjf-projects/cjf-template.git'
            ]
        ]
    ])
    println scmVars.GIT_BRANCH
      sh "git branch"
      mvnHome = tool 'M3'
   }
   
   stage('Trigger Build Wrapper'){
	    def job = build job: 'JDT_BUILD_WRAPPER_V5', parameters: [
            [
                $class: 'StringParameterValue', 
                name: 'cjf_build_project',
                value: 'build_cjf'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'SOURCE_GIT_URL',
                value: 'ssh://git@gitlab.fifthlayer.net:2222/cjf-projects/cjf-template.git'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'TAG',
                value: 'develop'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'ROOT_PROJECT_NAME',
                value: 'cjf-template'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'DEPLOY_ROOT',
                value: 'CJF-TEMPLATE'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'DT_DEPLOYMENT_DEV',
                value: 'cjf-template-devl'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'DT_DEPLOYMENT_TEST',
                value: 'cjf-template-test'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'DT_DEPLOYMENT_STAGING',
                value: 'cjf-template-stage'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'DT_DEPLOYMENT_PROD',
                value: 'cjf-template-prod'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'DT_EMAIL_ID',
                value: '_SH_-GL-US-CJF@Cevalogistics.com'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'DEV_APP_HOME',
                value: 'http://cjf-dev.ceva.cloud:8080/cjf-template'
            ],
            [
                $class: 'StringParameterValue', 
                name: 'JDT_BUILD_JOB_NAME',
                value : env.JOB_NAME
            ],
            [
                $class: 'StringParameterValue', 
                name: 'JDT_BUILD_JOB_NUMBER',
                value : env.BUILD_NUMBER
            ]
       	]
	}
	
}
