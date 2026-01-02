def call(Map config=[:]){
    def git_creds_id= config.git_creds_id ?: error("Enter Github Creds ID from Jenkins Global Credentials ")
    def docker_image = config.docker_image ?: error("Enter Docker Image Name to update in k8s manifest file")
    def image_tag = config.image_tag ?: error("Enter Image tag to update on manifest file")
    def k8s_deployment_file_path = config.k8s_deployment_file_path ?: error("Enter k8s deployment file path on github")
    def github_repo_name = config.github_repo_name ?: error("Enter github repo name to update")
    def git_branch_name = config.git_branch_name ?: error("Enter github repo branch name")

    withCredentials([usernamePassword(
        credentialsId: "${git_creds_id}",
        usernameVariable: "GIT_USER",
        passwordVariable: "GIT_PASS"
        )]){
            sh """
                 sed -i "s|image:.*|image: ${docker_image}:${image_tag}|g" ${k8s_deployment_file_path}
                 git add ${k8s_deployment_file_path}
                 git commit -m "UPDATED BY JENKINS"
                 git remote set-url origin "https://$GIT_PASS@github.com/$GIT_USER/${github_repo_name}.git"
                 git push origin ${git_branch_name}
                """
                }
}