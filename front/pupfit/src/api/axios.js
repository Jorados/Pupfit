import axios from 'axios';

const instance = axios.create({
    // 주석하는 이유 -> nginx의 프록시를 이용해서 백엔드서버에 접근가능하게 할것임.
    // baseURL: 'http://localhost:8080', // 기본 API 엔드포인트
    headers: {
        'Content-Type': 'application/json',
    },
});

// 인터셉터 설정 -> 여기서 모든 axios 요청할때 토큰을 request 헤더에 담는다.
instance.interceptors.request.use(
    config => {
        if (!config.url.includes('/login')) {
            const token = localStorage.getItem('token');
            if (token) {
                config.headers['Authorization'] = `${token}`;
            }
        }
        return config;
    },
    error => Promise.reject(error)
);

export default instance;
