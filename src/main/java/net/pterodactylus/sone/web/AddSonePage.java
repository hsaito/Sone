/*
 * Sone - AddSonePage.java - Copyright © 2010 David Roden
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

package net.pterodactylus.sone.web;

import net.pterodactylus.util.template.Template;

/**
 * This page lets the user add a Sone by URI.
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class AddSonePage extends SoneTemplatePage {

	/**
	 * Creates a new “add Sone” page.
	 *
	 * @param template
	 *            The template to render
	 * @param webInterface
	 *            The Sone web interface
	 */
	public AddSonePage(Template template, WebInterface webInterface) {
		super("addSone.html", template, "Page.AddSone.Title", webInterface);
	}

	//
	// TEMPLATEPAGE METHODS
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void processTemplate(Request request, Template template) throws RedirectException {
		super.processTemplate(request, template);
		final String soneKey = request.getHttpRequest().getPartAsStringFailsafe("request-uri", 256);
		new Thread(new Runnable() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void run() {
				webInterface.core().loadSone(soneKey);
			}
		}, "Sone Downloader").start();
	}

}
