import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBillet, defaultValue } from 'app/shared/model/billet.model';

export const ACTION_TYPES = {
  FETCH_BILLET_LIST: 'billet/FETCH_BILLET_LIST',
  FETCH_BILLET: 'billet/FETCH_BILLET',
  CREATE_BILLET: 'billet/CREATE_BILLET',
  UPDATE_BILLET: 'billet/UPDATE_BILLET',
  PARTIAL_UPDATE_BILLET: 'billet/PARTIAL_UPDATE_BILLET',
  DELETE_BILLET: 'billet/DELETE_BILLET',
  RESET: 'billet/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBillet>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type BilletState = Readonly<typeof initialState>;

// Reducer

export default (state: BilletState = initialState, action): BilletState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BILLET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BILLET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BILLET):
    case REQUEST(ACTION_TYPES.UPDATE_BILLET):
    case REQUEST(ACTION_TYPES.DELETE_BILLET):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_BILLET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BILLET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BILLET):
    case FAILURE(ACTION_TYPES.CREATE_BILLET):
    case FAILURE(ACTION_TYPES.UPDATE_BILLET):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_BILLET):
    case FAILURE(ACTION_TYPES.DELETE_BILLET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BILLET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_BILLET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BILLET):
    case SUCCESS(ACTION_TYPES.UPDATE_BILLET):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_BILLET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BILLET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/billets';

// Actions

export const getEntities: ICrudGetAllAction<IBillet> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BILLET_LIST,
    payload: axios.get<IBillet>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IBillet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BILLET,
    payload: axios.get<IBillet>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBillet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BILLET,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBillet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BILLET,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IBillet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_BILLET,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBillet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BILLET,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
