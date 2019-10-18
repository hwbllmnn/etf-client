/**
 * Copyright 2017-2019 European Union, interactive instruments GmbH
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This work was supported by the EU Interoperability Solutions for
 * European Public Administrations Programme (http://ec.europa.eu/isa)
 * through Action 1.17: A Reusable INSPIRE Reference Platform (ARE3NA).
 */
package de.interactive_instruments.etf.client.internal;

import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;

import de.interactive_instruments.etf.client.EtfCollection;
import de.interactive_instruments.etf.client.RemoteInvocationException;

/**
 * This class is only used internally and is not shared with the client of the library. The TranslationTemplateBundles do not have labels and descriptions.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */

class TranslationTemplateBundleCollectionCmd {

    private final static String PATH = "/TranslationTemplateBundles";

    private static class TranslationTemplateBundleCollection extends AbstractEtfCollection<TranslationTemplateBundle> {

        TranslationTemplateBundleCollection(final InstanceCtx ctx, final JSONArray jsonArray) {
            super(ctx, jsonArray);
        }

        @Override
        TranslationTemplateBundle doPrepare(final JSONObject jsonObject) {
            return new TranslationTemplateBundle(jsonObject, this);
        }
    }

    private final JsonGetRequest apiCall;
    private final InstanceCtx ctx;
    private TranslationTemplateBundleCollection cachedCollection;

    TranslationTemplateBundleCollectionCmd(final InstanceCtx ctx) {
        this.ctx = ctx;
        this.apiCall = new JsonGetRequest(URI.create(ctx.baseUrl.toString() + PATH), ctx);
    }

    synchronized EtfCollection<TranslationTemplateBundle> query() throws RemoteInvocationException {
        if (apiCall.upToDate()) {
            return cachedCollection;
        } else {
            final JSONArray result = apiCall.query().getJSONObject("EtfItemCollection")
                    .getJSONObject("translationTemplateBundles").getJSONArray("TranslationTemplateBundle");
            cachedCollection = new TranslationTemplateBundleCollection(this.ctx, result);
        }
        return cachedCollection;
    }
}
