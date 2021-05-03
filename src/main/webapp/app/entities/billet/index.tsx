import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Billet from './billet';
import BilletDetail from './billet-detail';
import BilletUpdate from './billet-update';
import BilletDeleteDialog from './billet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BilletUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BilletUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BilletDetail} />
      <ErrorBoundaryRoute path={match.url} component={Billet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BilletDeleteDialog} />
  </>
);

export default Routes;
