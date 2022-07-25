package com.example.jpa.nPlusOne;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NplusOneService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    public void setUp() {
        // create member
        Member member = new Member("my");
        Member followMember1 = new Member("follow1");
        Member followMember2 = new Member("follow2");
        memberRepository.save(member);
        memberRepository.save(followMember1);
        memberRepository.save(followMember2);

        // create follow
        Follow follow1 = Follow.builder().fromMember(member).toMember(followMember1).build();
        Follow follow2 = Follow.builder().fromMember(member).toMember(followMember2).build();
        followRepository.save(follow1);
        followRepository.save(follow2);

        // create post
        Post post1 = Post.builder().content("content1").member(followMember1).build();
        Post post2 = Post.builder().content("content2").member(followMember1).build();
        Post post3 = Post.builder().content("content3").member(followMember2).build();
        Post post4 = Post.builder().content("content4").member(followMember2).build();
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
    }

    public List<Post> getFeedsByGetter(Long lastPostId) {
        Member currentMember = getCurrentMember();

        List<Post> posts = new ArrayList<>();

        currentMember.getFollowings()
                .stream()
                .map(Follow::getToMember)
                .forEach(member -> posts.addAll(member.getPosts()));

        return posts.stream()
                .filter(post -> post.getId() < lastPostId)
                .sorted((a, b) -> (int) (b.getId() - a.getId()))
                .limit(5L)
                .collect(Collectors.toList());
    }

    public List<Post> getFeedsByFetchJoin(Long lastPostId) {
        Member currentMember = getCurrentMember();
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());

        return postRepository.findByFetchJoin(currentMember.getId(), lastPostId, pageRequest);
    }

    public List<Post> getFeedsByIn(Long lastPostId) {
        Member currentMember = getCurrentMember();

        List<Member> followings = currentMember.getFollowings()
                .stream()
                .map(Follow::getToMember)
                .collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
        return postRepository.findByIdLessThanAndMemberIn(lastPostId, followings, pageRequest);
    }

    public List<Post> getFeedsByJoin(Long lastPostId) {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
        return postRepository.findByJoinFollow(getCurrentMember().getId(), lastPostId, pageRequest);
    }

    private Member getCurrentMember() {
        return memberRepository.findById(1L).get();
    }
}