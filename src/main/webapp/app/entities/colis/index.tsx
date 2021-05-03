import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Colis from './colis';
import ColisDetail from './colis-detail';
import ColisUpdate from './colis-update';
import ColisDeleteDialog from './colis-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ColisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ColisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ColisDetail} />
      <ErrorBoundaryRoute path={match.url} component={Colis} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ColisDeleteDialog} />
  </>
);

export default Routes;
