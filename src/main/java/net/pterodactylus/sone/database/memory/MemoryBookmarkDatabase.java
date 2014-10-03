package net.pterodactylus.sone.database.memory;

import static com.google.common.collect.FluentIterable.from;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.pterodactylus.sone.data.Post;
import net.pterodactylus.sone.database.BookmarkDatabase;

import com.google.common.base.Function;

/**
 * Memory-based {@link BookmarkDatabase} implementation.
 *
 * @author <a href="mailto:bombe@pterodactylus.net">David ‘Bombe’ Roden</a>
 */
public class MemoryBookmarkDatabase implements BookmarkDatabase {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final MemoryDatabase memoryDatabase;
	private final Set<String> bookmarkedPosts = new HashSet<String>();

	public MemoryBookmarkDatabase(MemoryDatabase memoryDatabase) {
		this.memoryDatabase = memoryDatabase;
	}

	@Override
	public void bookmarkPost(String postId) {
		lock.writeLock().lock();
		try {
			bookmarkedPosts.add(postId);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void bookmarkPost(Post post) {
		lock.writeLock().lock();
		try {
			bookmarkedPosts.add(post.getId());
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void unbookmarkPost(Post post) {
		lock.writeLock().lock();
		try {
			bookmarkedPosts.remove(post.getId());
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public boolean isPostBookmarked(Post post) {
		lock.readLock().lock();
		try {
			return bookmarkedPosts.contains(post.getId());
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public Set<Post> getBookmarkedPosts() {
		lock.readLock().lock();
		try {
			return from(bookmarkedPosts).transformAndConcat(
					new Function<String, Iterable<Post>>() {
						@Override
						public Iterable<Post> apply(String postId) {
							return memoryDatabase.getPost(postId).asSet();
						}
					}).toSet();
		} finally {
			lock.readLock().unlock();
		}
	}

}
