import { createStore } from 'vuex';
import {format} from "date-fns";

export default createStore({
    state: {
        token: localStorage.getItem('token') || '',
    },
    mutations: {
        setToken(state, token) {
            state.token = token;
            localStorage.setItem('token', token);
        },
        clearToken(state) {
            state.token = '';
            localStorage.removeItem('token');
        },
    },
    actions: {
        login({ commit }, token) {
            commit('setToken', token);
        },
        logout({ commit }) {
            commit('clearToken');
        },
    },
    getters: {
        isAuthenticated: state => !!state.token,
    },
});

export const formatDate = (dateString) => {
    const date = new Date(dateString);
    return format(date, 'yyyy년 MM월 dd일 HH시 mm분');
};
