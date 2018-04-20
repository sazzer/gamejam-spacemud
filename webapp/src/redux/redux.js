import {createStore} from 'redux-box';
import {userExistsModule, accessTokenModule} from '../authentication'
import {createUserModule, userProfileModule} from '../users'

export default createStore([
    userExistsModule,
    accessTokenModule,
    createUserModule,
    userProfileModule
]);
