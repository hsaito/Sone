/*
 * Sone - PostReplyImpl.java - Copyright © 2010–2013 David Roden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.pterodactylus.sone.data.impl;

import net.pterodactylus.sone.core.PostProvider;
import net.pterodactylus.sone.data.Post;
import net.pterodactylus.sone.data.PostReply;
import net.pterodactylus.sone.data.Sone;

/**
 * Simple {@link PostReply} implementation.
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class PostReplyImpl extends ReplyImpl<PostReply> implements PostReply {

	/** The post provider. */
	private final PostProvider postProvider;

	/** The Post this reply refers to. */
	private final String postId;

	/**
	 * Creates a new reply.
	 *
	 * @param postProvider
	 *            The post provider
	 * @param id
	 *            The ID of the reply
	 * @param sone
	 *            The Sone of the reply
	 * @param time
	 *            The time of the reply
	 * @param text
	 *            The text of the reply
	 * @param postId
	 *            The ID of the post this reply refers to
	 */
	public PostReplyImpl(PostProvider postProvider, String id, Sone sone, long time, String text, String postId) {
		super(id, sone, time, text);
		this.postProvider = postProvider;
		this.postId = postId;
	}

	//
	// ACCESSORS
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Post getPost() {
		return postProvider.getPost(postId);
	}

}
