<template>
  <header class="container-fluid">
					<nav class="navbar navbar-expand-xs navbar-dark bg-dark fixed-top">
							<button class="navbar-toggler mr-auto" type="button" data-toggle="collapse"
								data-target="#navbarsExampleDefault"
								aria-controls="navbarsExampleDefault" aria-expanded="false"
								aria-label="Toggle navigation">
								<span class="navbar-toggler-icon"></span>
							</button>

							<!--未認証時：ログインボタン-->
							<div class="d-flex flex-row">
								<a href="/login">
									<button type="button" class="btn btn-dark">
										<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-person-check-fill" viewBox="0 0 16 16">
											<path fill-rule="evenodd" d="M15.854 5.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 7.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
											<path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
										</svg>
									</button>
								</a>
							</div>

							<!--認証済：マイページへのリンク-->
							<!--Vueについて、他のページでは有効であったが、th:fragmentとして読み込む場合は機能しない-->
							

							<div class="collapse navbar-collapse" id="navbarsExampleDefault">
								<hr class="divider my-2" />
								<ul class="navbar-nav me-auto mb-2 mb-md-0">
								<li class="nav-item"><a class="nav-link" aria-current="page" href="/">トップページ</a></li>
									<li class="nav-item"><a class="nav-link" aria-current="page" href="/description">コアラマニアとは</a></li>
									<li class="nav-item"><a class="nav-link" href="/insert">コアラを登録する</a></li>
									<li class="nav-item"><a class="nav-link" href="/search">コアラを閲覧する</a></li>
									<li class="nav-item"><a class="nav-link" aria-current="page" href="/post/postInsert/1">写真を投稿する</a></li>
									<li class="nav-item"><a class="nav-link"
										href="https://tayori.com/form/e976498f95636ad99bb9b6dbe901499a642cd107">お問い合わせ</a></li>
									<li>
										
									</li>
								</ul>
                            </div>
					</nav>
				</header>
    <slot />

    <div id="footer">
        <footer class="border-top">
            <div class="d-flex flex-wrap">
                <p class="p-1 col-3 text-center"><a href="/description">コアラマニアとは</a></p>
                <p class="p-1 col-3 text-center"><a href="/terms">利用規約</a></p>
                <p class="p-1 col-3 text-center"><a href="/privacypolicy">プライバシーポリシー</a></p>
                <p class="p-1 col-3 text-center"><a href="https://tayori.com/form/e976498f95636ad99bb9b6dbe901499a642cd107">お問い合わせ</a></p>
            </div>
        </footer>
    </div>
</template>