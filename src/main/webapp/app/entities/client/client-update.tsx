import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientUpdate = (props: IClientUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { clientEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/client' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.bornDate = convertDateTimeToServer(values.bornDate);

    if (errors.length === 0) {
      const entity = {
        ...clientEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sengpApp.client.home.createOrEditLabel" data-cy="ClientCreateUpdateHeading">
            <Translate contentKey="sengpApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : clientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="client-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="client-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstnameLabel" for="client-firstname">
                  <Translate contentKey="sengpApp.client.firstname">Firstname</Translate>
                </Label>
                <AvField id="client-firstname" data-cy="firstname" type="text" name="firstname" />
              </AvGroup>
              <AvGroup>
                <Label id="lastnameLabel" for="client-lastname">
                  <Translate contentKey="sengpApp.client.lastname">Lastname</Translate>
                </Label>
                <AvField id="client-lastname" data-cy="lastname" type="text" name="lastname" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="client-email">
                  <Translate contentKey="sengpApp.client.email">Email</Translate>
                </Label>
                <AvField id="client-email" data-cy="email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="passwordLabel" for="client-password">
                  <Translate contentKey="sengpApp.client.password">Password</Translate>
                </Label>
                <AvField id="client-password" data-cy="password" type="text" name="password" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="client-telephone">
                  <Translate contentKey="sengpApp.client.telephone">Telephone</Translate>
                </Label>
                <AvField id="client-telephone" data-cy="telephone" type="string" className="form-control" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label id="bornDateLabel" for="client-bornDate">
                  <Translate contentKey="sengpApp.client.bornDate">Born Date</Translate>
                </Label>
                <AvInput
                  id="client-bornDate"
                  data-cy="bornDate"
                  type="datetime-local"
                  className="form-control"
                  name="bornDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.clientEntity.bornDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cinLabel" for="client-cin">
                  <Translate contentKey="sengpApp.client.cin">Cin</Translate>
                </Label>
                <AvField id="client-cin" data-cy="cin" type="text" name="cin" />
              </AvGroup>
              <AvGroup>
                <Label id="photoLabel" for="client-photo">
                  <Translate contentKey="sengpApp.client.photo">Photo</Translate>
                </Label>
                <AvField id="client-photo" data-cy="photo" type="text" name="photo" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/client" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  clientEntity: storeState.client.entity,
  loading: storeState.client.loading,
  updating: storeState.client.updating,
  updateSuccess: storeState.client.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientUpdate);
