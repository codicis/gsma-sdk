plugins {
    alias(libs.plugins.antora)
}
antora {
    playbook = file("antora-playbook.yml")
}