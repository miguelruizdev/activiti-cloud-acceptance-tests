/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.qa.steps;

import java.util.Optional;
import java.util.UUID;

import net.thucydides.core.annotations.Step;
import org.activiti.cloud.organization.api.Application;
import org.activiti.cloud.qa.model.modeling.EnableModelingContext;
import org.activiti.cloud.qa.model.modeling.ModelingIdentifier;
import org.activiti.cloud.qa.service.ModelingApplicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.Link.REL_SELF;

/**
 * Modeling applications org.activiti.cloud.acceptance.steps
 */
@EnableModelingContext
public class ModelingApplicationsSteps extends ModelingContextSteps<Application> {

    @Autowired
    private ModelingApplicationsService modelingApplicationsService;

    @Step
    public Resource<Application> create(String applicationName) {
        String id = UUID.randomUUID().toString();
        Application application = mock(Application.class);
        doReturn(id).when(application).getId();
        doReturn(applicationName).when(application).getName();
        return create(id,
                      application);
    }

    @Step
    public void updateApplicationName(String newApplicationName) {
        Resource<Application> currentContext = checkAndGetCurrentContext(Application.class);
        Application application = currentContext.getContent();
        application.setName(newApplicationName);

        modelingApplicationsService.updateByUri(currentContext.getLink(REL_SELF).getHref(),
                                                application);
    }

    @Step
    public void checkCurrentApplicationName(String applicationName) {
        updateCurrentModelingObject();
        Resource<Application> currentContext = checkAndGetCurrentContext(Application.class);
        assertThat(currentContext.getContent().getName()).isEqualTo(applicationName);
    }

    @Step
    public void checkApplicationNotFound(ModelingIdentifier identifier) {
        assertThat(findAll().getContent()
                           .stream()
                           .map(Resource::getContent)
                           .filter(identifier::test)
                           .findAny())
                .isEmpty();
    }

    @Override
    protected Optional<String> getRel() {
        return Optional.empty();
    }

    @Override
    public ModelingApplicationsService service() {
        return modelingApplicationsService;
    }
}
