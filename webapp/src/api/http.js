import axios from 'axios';

/** The base URL to call */
const URL_BASE = process.env.REACT_APP_API_URL;

/** The HTTP Client to use */
export const httpClient = axios.create({
    baseURL: URL_BASE,
    timeout: 1000
});
