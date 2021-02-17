# pipeline-templates-dsl

generates an instance of a (dedicated) [CloudBees Pipeline Template Catalog](https://docs.cloudbees.com/docs/admin-resources/latest/pipeline-templates-user-guide/) by using JOB-DSL



# Pre-requirements
* CloudBees CI Controller
* Add [this Pipeline Templateb Catalog](https://github.com/pipeline-demo-caternberg/pipeline-examples) to your controller
* Create a PipelineJob with this repo as a SCM source
  * You will fail during the first run because script approval will block
  * approve the script under manage Jenkins -> script approvals and run again
* The example implementation is related to this [multibranchPipeline template](https://github.com/pipeline-demo-caternberg/pipeline-examples/tree/master/templates/multibranchPipeline)
* For other template instances the `Seed. groovy` implementation needs to be adjusted
  * `'model'('Pipeline-Tem.c3qk18.log-Examples/multibranchPipeline')`
  * as well as the parameters see `'values'(class: 'tree-map') ` and the related [template.yaml](https://github.com/pipeline-demo-caternberg/pipeline-examples/blob/master/templates/multibranchPipeline/template.yaml) 