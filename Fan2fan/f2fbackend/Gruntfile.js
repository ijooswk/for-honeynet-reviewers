module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        concat: {
            css: {
                src: [
                    'src/main/resources/static/lib/bootstrap/dist/css/bootstrap.css',
                    'src/main/resources/static/lib/bootstrap-3-datepicker/css/datepicker3.css',
                    'src/main/resources/static/lib/font-awesome/css/font-awesome.min.css',
                    'src/main/resources/static/lib/jquery-steps/demo/css/jquery.steps.css',
                    'src/main/resources/static/lib/dropzone/downloads/css/dropzone.css',
                    'src/main/resources/static/css/**/*.css'
                ],
                dest: 'src/main/resources/static/dist/ff.css'
            },
            js : {
                src : [
                    'src/main/resources/static/lib/jquery/jquery.min.js',
                    'src/main/resources/static/lib/bootstrap/dist/js/bootstrap.min.js',
                    'src/main/resources/static/lib/bootstrap-3-datepicker/js/bootstrap-datepicker.js',
                    'src/main/resources/static/lib/jquery-steps/build/jquery.steps.min.js',
                    'src/main/resources/static/lib/jquery-validation/dist/jquery.validate.min.js',
                    'src/main/resources/static/lib/dropzone/downloads/dropzone.min.js',
                    'src/main/resources/static/lib/jquery-file-upload/js/vendor/jquery.ui.widget.js',
                    'src/main/resources/static/lib/jquery-file-upload/js/jquery.iframe-transport.js',
                    'src/main/resources/static/lib/jquery-file-upload/js/jquery.fileupload.js',
                    'src/main/resources/static/lib/unveil/jquery.unveil.js',
                    'src/main/resources/static/js/**/*.js'
                ],
                dest: 'src/main/resources/static/dist/ff.js'
            }
        },
        cssmin: {
            css: {
                src: 'src/main/resources/static/dist/ff.css',
                dest: 'src/main/resources/static/dist/ff.min.css'
            }
        },
        uglify: {
            options: {
                compress: {
                    drop_console: true
                }
            },
            js: {
                files: {
                    'src/main/resources/static/dist/ff.min.js': ['src/main/resources/static/dist/ff.js']
                }
            }
        },
        watch: {
            files: ['src/main/resources/static/css/**/*', 'src/main/resources/static/js/**/*', 'src/main/resources/static/lib/**/*'],
            tasks: ['concat', 'cssmin', 'uglify']
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.registerTask('default', [ 'concat:css', 'cssmin:css', 'concat:js', 'uglify:js' ]);
};
