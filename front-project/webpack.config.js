const path = require('path');

module.exports = {
    module: {
        rules: [
            {
                test: /\.(png|jpe?g|gif|svg)$/i, // 이미지 파일 형식 처리
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[name].[ext]',
                            outputPath: 'assets/img',
                            esModule: false,
                        },
                    },
                ],
            },
        ],
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'), // Vue 파일들에서 '@' 경로로 src 디렉토리를 참조할 수 있게 설정
        },
    },
};