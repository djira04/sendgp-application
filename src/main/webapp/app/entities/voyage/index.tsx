import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Voyage from './voyage';
import VoyageDetail from './voyage-detail';
import VoyageUpdate from './voyage-update';
import VoyageDeleteDialog from './voyage-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VoyageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VoyageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VoyageDetail} />
      <ErrorBoundaryRoute path={match.url} component={Voyage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VoyageDeleteDialog} />
  </>
);

export default Routes;
