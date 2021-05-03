import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Voyageur from './voyageur';
import VoyageurDetail from './voyageur-detail';
import VoyageurUpdate from './voyageur-update';
import VoyageurDeleteDialog from './voyageur-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VoyageurUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VoyageurUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VoyageurDetail} />
      <ErrorBoundaryRoute path={match.url} component={Voyageur} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VoyageurDeleteDialog} />
  </>
);

export default Routes;
