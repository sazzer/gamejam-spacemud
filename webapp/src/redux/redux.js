import {createStore} from 'redux-box';
import {userExistsModule, accessTokenModule, authenticateModule} from '../authentication'
import {createUserModule, userProfileModule} from '../users'

export default createStore([
    userExistsModule,
    accessTokenModule,
    authenticateModule,
    createUserModule,
    userProfileModule
]);
