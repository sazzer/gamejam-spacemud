package uk.co.grahamcox.space.hal

import java.net.URI

/**
 * Representation of a single Link in a HAL document
 * @property href The href of the link
 * @property templated Whether the link is templated or not
 * @property type The mediatype of the document at the other end of the link
 * @property deprecation If deprecated, the reason why
 * @property name A name for the link, if multiple are provided for the same relation
 * @property profile A URI that hints about the profile of the target resource
 * @property title A human readable title of the link
 * @property hreflang The language of the linked resource
 */
data class Link(
        val href: String,
        val templated: Boolean = false,
        val type: String? = "application/hal+json",
        val deprecation: String? = null,
        val name: String? = null,
        val profile: URI? = null,
        val title: String? = null,
        val hreflang: String? = null
)
