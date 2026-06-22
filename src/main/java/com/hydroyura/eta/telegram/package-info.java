/**
 * Telegram bot adapter.
 * <p>
 * Receives messages from Telegram, parses commands, and delegates to domain use cases.
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {"teacher", "student", "dictionary"}
)
package com.hydroyura.eta.telegram;
