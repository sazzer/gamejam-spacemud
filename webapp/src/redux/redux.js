import {createStore} from 'redux-box';
import { routerMiddleware, routerReducer } from 'react-router-redux';
import createHistory from 'history/createBrowserHistory';
import {userExistsModule, accessTokenModule, authenticateModule} from '../authentication'
import {createUserModule, userProfileModule} from '../users'

export const history = createHistory();

const reduxBoxConfig = {
    middlewares: [
        routerMiddleware(history)
    ],
    reducers: {
        routing: routerReducer
    }
};

export const store = createStore([
    userExistsModule,
    accessTokenModule,
    authenticateModule,
    createUserModule,
    userProfileModule
], reduxBoxConfig);
