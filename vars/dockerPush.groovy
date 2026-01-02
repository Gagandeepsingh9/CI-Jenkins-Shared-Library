def call(Map config=[:]){
    def docker_creds_id = config.docker_creds_id ?: error("Enter Docker Creds ID from Jenkins Global Credentials")
    def docker_image = config.docker_image ?: error("Enter Image Name to push to Dockerhub")
    def image_tag = config.image_tag ?: error("Enter Image tag")

    withCredentials([usernamePassword(
        credentialsId: "${docker_creds_id}",
        usernameVariable: "docker_username",
        passwordVariable: "docker_password"
        )]){
            sh """ echo $docker_password | docker login -u $docker_username --password-stdin """
            sh """ docker push ${docker_image}:${image_tag} """
            }
}