import axios, { Method } from 'axios';

type RequestaParams = {
    method?: Method;
    url: string;
    data?: object;
    params?: object;
}

const BASE_URL = 'http://localhost:3000';

const makeRequest = ({method = 'GET', url, data, params} : RequestaParams) =>{
    return axios({
        method: method,
        url: `${BASE_URL}${url}`,
        data: data,
        params: params
    });
}

export default makeRequest;