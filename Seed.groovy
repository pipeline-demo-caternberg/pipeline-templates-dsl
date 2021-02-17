multibranchPipelineJob('PT-Instance-seed-by-dsl') {
    configure { project ->
        project / 'properties' / 'com.cloudbees.pipeline.governance.templates.classic.multibranch.GovernanceMultibranchPipelinePropertyImpl'(plugin: "cloudbees-workflow-template@3.12") << 'instance' {
            'model'('Pipeline-Tem.c3qk18.log-Examples/gitHubOrganisationPipeline')
            'values'(class: 'tree-map') {
                'entry' {
                    'string'("githubToken") {}
                    'string'("githubuseraccesstoken") {}
                }
                'entry' {
                    'string'("name") {}
                    'string'("maven-example") {}
                }
                'entry' {
                    'string'("organsisation") {}
                    'string'("pipeline-demo-caternberg") {}
                }
                'entry' {
                    'string'("repoName") {}
                    'string'("maven-executable-jar-example") {}
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
                                    'repoOwner'('pipeline-demo-caternberg')
                                    'repository'('maven-executable-jar-example')
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
            'pruneDeadBranches'('true') {}
            'daysToKeep'('-1') {}
            'numToKeep'('-1') {}

        }

        project.remove(project / factory)
        project << 'factory'(class: 'com.cloudbees.pipeline.governance.templates.classic.multibranch.FromTemplateBranchProjectFactory', plugin: 'cloudbees-workflow-template@3.12') {
            'owner'(class: 'org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject', reference: '../..') {}
            'catalogName'('Pipeline Template Catalog Examples') {}
            'templateDirectory'('gitHubOrganisationPipeline') {}
            'markerFile'('pom.xml') {}

        }

        /*  project.remove(project / 'triggers')
          {
              'com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger'(plugin: 'cloudbees-folder@6.15')
              {
                              'spec'('H H/4 * * *')
                              'interval'('86400000')
               }
          }
         */
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