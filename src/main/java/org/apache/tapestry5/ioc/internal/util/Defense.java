// Copyright 2006 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.ioc.internal.util;

import org.apache.tapestry5.ioc.Messages;

/**
 * Static utility methods for defensive programming.
 */
@Deprecated
public final class Defense
{
    private Defense()
    {
    }

    private static final Messages MESSAGES = MessagesImpl.forClass(UtilMessages.class);
    
    @Deprecated
    static String noReturnValueAccepted(String eventType, String componentId, Object returnValue)
    {
        return MESSAGES.format("no-return-value-accepted", eventType, componentId, String
                .valueOf(returnValue));
    }
    
    /**
     * Checks that a method parameter value is not null, and returns it.
     *
     * @param <T>           the value type
     * @param value         the value (which is checked to ensure non-nullness)
     * @param parameterName the name of the parameter, used for exception messages
     * @return the value
     * @throws IllegalArgumentException if the value is null
     */
    @Deprecated
    public static <T> T notNull(T value, String parameterName)
    {
        if (value == null) throw new IllegalArgumentException(String.format("Parameter %s was null.", parameterName));

        return value;
    }

    /**
     * Checks that a parameter value is not null and not empty.
     *
     * @param value         value to check (which is returned)
     * @param parameterName the name of the parameter, used for exception messages
     * @return the value, trimmed, if non-blank
     * @throws IllegalArgumentException if the value is null or empty
     */
    @Deprecated
    public static String notBlank(String value, String parameterName)
    {
        if (value != null)
        {
            String trimmedValue = value.trim();

            if (!trimmedValue.equals("")) return trimmedValue;
        }

        throw new IllegalArgumentException(String.format("Parameter %s was null or contained only whitespace.", parameterName));
    }

    /**
     * Checks that the provided value is not null, and may be cast to the desired type.
     *
     * @param <T>
     * @param parameterValue
     * @param type
     * @param parameterName
     * @return the casted value
     * @throws IllegalArgumentException if the value is null, or is not assignable to the indicated type
     */
    @Deprecated
    public static <T> T cast(Object parameterValue, Class<T> type, String parameterName)
    {
        notNull(parameterValue, parameterName);

        if (!type.isInstance(parameterValue))
            throw new IllegalArgumentException(String.format("Parameter %s (%s) is not assignable to type %s.", parameterName, parameterValue, type));

        return type.cast(parameterValue);
    }
}
