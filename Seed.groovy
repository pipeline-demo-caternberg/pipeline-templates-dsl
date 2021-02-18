//jobName: The name of the generated Job in Jenkins
def myJobName = "PT-by-dsl"
//default catalogName, see catalog.yaml of the PT catalog
def catalog = "Pipeline Template Catalog Examples"
//The the GH org to scan
def ghOrganisation ='pipeline-demo-caternberg'
//THe GH repo within the GHorg to scan
//def ghRepo ='maven-executable-jar-example'
//add more or scan via GHRestAPI for all repos
def ghRepos=["maven-executable-jar-example","spring-boot-demo"]

//MarkerFile, see template.yaml of PT catalog
def marker ='pom.xml'
//name of the template sub-dir within the PT repo
def templateDir = "multibranchPipeline"
//link to the PT catalog defined in Jenkins
def ptModel = 'Pipeline-Tem.c3qk18.log-Examples/multibranchPipeline'

//for (number in 1..3 ) {
for (ghRepo in ghRepos) {
    print ghRepo
    multibranchPipelineJob(myJobName + "_" + ghRepo) {
        configure { project ->
            project / 'properties' / 'com.cloudbees.pipeline.governance.templates.classic.multibranch.GovernanceMultibranchPipelinePropertyImpl'(plugin: "cloudbees-workflow-template@3.12") << 'instance' {
                'model'(ptModel)
                'values'(class: 'tree-map') {
                    'entry' {
                        'string'("githubToken")
                        'string'("githubuseraccesstoken")
                    }
                    'entry' {
                        'string'("organsisation")
                        'string'(ghOrganisation)
                    }
                    'entry' {
                        'string'("repoName")
                        'string'(ghRepo)
                    }
                }
            }
            project.remove(project / folderViews)
            project << 'folderViews'(class: 'jenkins.branch.MultiBranchProjectViewHolder', plugin: 'branch-api@2.6.2') {
                'owner'(class: 'org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject', reference: '../..') {}
            }
            project.remove(project / sources)
            project / sources {
                'jenkins.branch.BranchSource'(plugin: 'branch-api@2.6.2')
                        {
                            'source'(class: 'org.jenkinsci.plugins.github_branch_source.GitHubSCMSource', plugin: 'github-branch-source@2.9.3')
                                    {
                                        'id'('1234556')
                                        'apiUri'('https://api.github.com')
                                        'credentialsId'('githubuseraccesstoken')
                                        'repoOwner'(ghOrganisation)
                                        'repository'(ghRepo)
                                        'traits' {
                                            'org.jenkinsci.plugins.github__branch__source.BranchDiscoveryTrait'
                                                    {
                                                        'strategyId'(1)
                                                    }
                                            'org.jenkinsci.plugins.github__branch__source.OriginPullRequestDiscoveryTrait'
                                                    {
                                                        'strategyId'(1)
                                                    }
                                            'org.jenkinsci.plugins.github__branch__source.ForkPullRequestDiscoveryTrait'
                                                    {
                                                        'strategyId'(1)
                                                        'trust'('class': 'org.jenkinsci.plugins.github_branch_source.ForkPullRequestDiscoveryTrait$TrustPermission')

                                                    }
                                        }
                                    }
                        }
            }

            project.remove(project / orphanedItemStrategy)
            project << orphanedItemStrategy(class: 'com.cloudbees.hudson.plugins.folder.computed.DefaultOrphanedItemStrategy', plugin: 'cloudbees-folder@6.15') {
                'pruneDeadBranches'('true')
                'daysToKeep'(-1)
                'numToKeep'(-1)

            }

            project.remove(project / factory)
            project << 'factory'(class: 'com.cloudbees.pipeline.governance.templates.classic.multibranch.FromTemplateBranchProjectFactory', plugin: 'cloudbees-workflow-template@3.12') {
                'owner'(class: 'org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject', reference: '../..') {}
                'catalogName'(catalog)
                'templateDirectory'(templateDir)
                'markerFile'(marker)
            }

            // project.remove(project / 'triggers')
            project / 'triggers' << 'com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger'(plugin: 'cloudbees-folder@6.15') {
                'spec'('H H/4 * * *') {}
                'interval'('86400000') {}
            }

            project.remove(project / healthMetrics)
            project << healthMetrics {
                'com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric'(plugin: 'cloudbees-folder@6.15')
                        {
                            'nonRecursive'('false')
                        }
                'com.cloudbees.hudson.plugins.folder.health.AverageChildHealthMetric'(plugin: 'cloudbees-folders-plus@3.10')
            }
        }
    }
}