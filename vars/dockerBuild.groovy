def call(Map config=[:]){
    def image = config.image ?: error("image name is required")
    def tag = config.tag ?: error("tag is required")
    def dockerfile = config.dockerfile ?: "Dockerfile"
    def context = config.context ?: "."

    sh """ docker build -t ${image}:${tag} -f ${dockerfile} ${context} """
}