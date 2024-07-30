import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080', // 기본 API 엔드포인트
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
                config.headers['Authorization'] = `Bearer ${token}`;
            }
        }
        return config;
    },
    error => Promise.reject(error)
);

export default instance;